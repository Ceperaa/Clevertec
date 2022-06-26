package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.dto.ProductDto;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.Product;
import ru.clevertec.check.runner.repository.impl.CheckRepositoryImpl;
import ru.clevertec.check.runner.streamIO.impl.CheckIO;

import java.util.List;

import static org.mockito.BDDMockito.given;

class CheckRunnerServicesImplTest {

    private final CheckRunnerServicesImpl checkRunnerServices;
    @Mock
    private ProductServicesImpl productServices;
    @Mock
    private CheckRepositoryImpl checkRepository;
    @Mock
    private CheckIO checkIO;
    @Mock
    ModelMapper modelMapper;

    private Check check;

    CheckRunnerServicesImplTest() {
        MockitoAnnotations.initMocks(this);
        this.checkRunnerServices = new CheckRunnerServicesImpl(
                productServices
                , checkRepository
                , checkIO
                , modelMapper
        );
    }

    @BeforeEach
    void setUp() {
        check = new Check();
        check.setId(1);
        check.setProductList(List.of(new Product()));
    }

    @Test
    void exportFile() {
    }

    @Test
    void mapToCheckDto() {
        CheckDto checkDto = new CheckDto();
        given(modelMapper.map(check, CheckDto.class)).willReturn(checkDto);
        Assertions.assertEquals(checkRunnerServices.mapToCheckDto(check,List.of(new ProductDto())), checkDto);
    }

    @Test
    void add() throws Exception {
        given(checkRepository.add(check)).willReturn(check);
        Assertions.assertEquals(checkRunnerServices.add(check), check);
    }
}