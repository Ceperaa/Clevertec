package ru.clevertec.check.runner.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.check.runner.dto.DiscountCardDtoForCreate;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.repository.RepositoryEntity;
import ru.clevertec.check.runner.services.DiscountCardService;
import ru.clevertec.check.runner.util.exception.ObjectNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountCardServiceImpl implements DiscountCardService {

    private final RepositoryEntity<DiscountCard> discountCardRepository;
    private final ModelMapper modelMapper;

    public DiscountCardServiceImpl(RepositoryEntity<DiscountCard> discountCardRepository, ModelMapper modelMapper) {
        this.discountCardRepository = discountCardRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<DiscountCard> findById(long id) throws SQLException {
        return discountCardRepository.findById(id);
    }

    public List<DiscountCard> allListDiscountCard() throws IOException, SQLException {
        return discountCardRepository.findAll();
    }

    public DiscountCard saveCard(DiscountCardDtoForCreate card) throws IOException, SQLException {
        return discountCardRepository.add(modelMapper.map(card,DiscountCard.class));
    }

    public void deleteCard(long id) throws SQLException, ObjectNotFoundException, IOException {
        DiscountCard discountCard = findById(id).orElseThrow(() -> new ObjectNotFoundException("DiscountCard id: " + id + "not found"));
        discountCardRepository.delete(discountCard.getId());
    }
}
