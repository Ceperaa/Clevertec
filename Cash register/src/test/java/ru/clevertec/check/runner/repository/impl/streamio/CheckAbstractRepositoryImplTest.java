package ru.clevertec.check.runner.repository.impl.streamio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.streamIO.impl.CheckIO;

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;

class CheckAbstractRepositoryImplTest {

    private final CheckRepositoryImpl checkRepository;
    @Mock
    private Map<Long,Check> checkMap;
    @Mock
    private CheckIO checkIO;

    private Check check;

    CheckAbstractRepositoryImplTest() {
        MockitoAnnotations.initMocks(this);
        this.checkRepository = new CheckRepositoryImpl(checkMap,checkIO);
    }

    @BeforeEach
    void setUp() {
        check = new Check();
        check.setId(1L);
    }

    @Test
    void findAll() throws Exception {
        List<Check> list = List.of(check);
        given(checkIO.importServiceFile()).willReturn((List)list);
        Assertions.assertEquals(checkRepository.findAll(), list);
    }

    @Test
    void add() {
        given(checkMap.get(1L)).willReturn(check);
        Assertions.assertEquals(checkRepository.findById(1L), check);
    }
}