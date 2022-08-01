package ru.clevertec.check.runner.repository.impl.streamio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
public class DiscountCardRepositoryImpl extends RepositoryEntityImpl<DiscountCard> {

    private final Map<Long, DiscountCard> map;
    private final IStreamIO discountCardIO;
    @Value("${cardId}")
    private long increment;

    public DiscountCardRepositoryImpl(Map<Long, DiscountCard> map, IStreamIO discountCardIO) {
        super(map, discountCardIO);
        this.map = map;
        this.discountCardIO = discountCardIO;
    }

    @Override
    public List<DiscountCard> findAll(int limit, int offset) {
        return (List) discountCardIO.importServiceFile();
    }

    @Override
    public DiscountCard add(DiscountCard o) {
        increment++;
        o.setId(increment);
        map.put(o.getId(), o);
        discountCardIO.exportFile(List.of(o), false);
        super.setFieldProperty();
        return o;
    }

    @Override
    public DiscountCard update(DiscountCard discountCard) {

        long id = discountCard.getId();
        if (super.findById(id).isPresent()) {
            super.delete(id);
        }
        map.put(id, discountCard);
        discountCardIO.exportFile(List.of(discountCard), false);
        return discountCard;
    }

    @Override
    protected void setProperty(Properties properties) {
        properties.setProperty("cardId", String.valueOf(increment));
    }
}
