package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.clevertec.check.runner.dto.CheckDto;
import ru.clevertec.check.runner.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.Check;
import ru.clevertec.check.runner.model.ProductInformation;
import ru.clevertec.check.runner.repository.impl.streamio.CheckRepositoryImpl;

import java.util.List;

import static org.mockito.BDDMockito.given;

class CheckRunnerServiceImplTest {

    private final CheckRunnerServiceImpl checkRunnerServices;
    @Mock
    private ProductServiceImpl productServices;
    @Mock
    private CheckRepositoryImpl checkRepository;
    @Mock
    private ProductInformationServiceImpl productInformationService;
    @Mock
    ModelMapper modelMapper;

    private Check check;

    CheckRunnerServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        this.checkRunnerServices = new CheckRunnerServiceImpl(
                productServices
                , checkRepository
                , productInformationService
                , modelMapper
        );
    }

    @BeforeEach
    void setUp() {
        check = new Check();
        check.setId(1);
        check.setProductList(List.of(new ProductInformation()));
    }

    @Test
    void exportFile() {
    }

    @Test
    void mapToCheckDto() {
        CheckDto checkDto = new CheckDto();
        given(modelMapper.map(check, CheckDto.class)).willReturn(checkDto);
        Assertions.assertEquals(checkRunnerServices.mapToCheckDto(check,List.of(new ProductInformationDto())), checkDto);
    }

    @Test
    void add() throws Exception {
        given(checkRepository.add(check)).willReturn(check);
        Assertions.assertEquals(checkRunnerServices.saveCheck(check), check);
    }
}