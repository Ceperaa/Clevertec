package ru.clevertec.check.runner.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.runner.model.dto.CheckDto;
import ru.clevertec.check.runner.model.dto.ProductInformationDto;
import ru.clevertec.check.runner.model.entity.Check;
import ru.clevertec.check.runner.model.entity.ProductInformation;
import ru.clevertec.check.runner.repository.CheckRepository;
import ru.clevertec.check.runner.util.mapperMapstruct.CheckMapper;

import java.util.List;

import static org.mockito.BDDMockito.given;

class CheckRunnerServiceImplTest {

    private final CheckRunnerServiceImpl checkRunnerServices;
    @Mock
    private ProductServiceImpl productServices;
    @Mock
    private CheckRepository checkRepository;
    @Mock
    private ProductInformationServiceImpl productInformationService;
    @Mock
    private CheckMapper checkMapper;


    private Check check;

    CheckRunnerServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        this.checkRunnerServices = new CheckRunnerServiceImpl(
                productServices,
                checkRepository,
                productInformationService,
                checkMapper
        );
    }

    @BeforeEach
    void setUp() {
        check = new Check();
        check.setId(1L);
        check.setProductList(List.of(new ProductInformation()));
    }

    @Test
    void exportFile() {
    }

    @Test
    @Disabled
    void mapToCheckDto() {
        CheckDto checkDto = new CheckDto();
        given(checkMapper.entityToDto(check)).willReturn(checkDto);
        Assertions.assertEquals(checkRunnerServices.mapToCheckDto(check,List.of(new ProductInformationDto())), checkDto);
    }

    @Test
    @Disabled
    void add() throws Exception {
        given(checkRepository.save(check)).willReturn(check);
        Assertions.assertEquals(checkRunnerServices.saveCheck(check), check);
    }
}