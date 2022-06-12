package ru.clevertec.check.runner.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.check.runner.model.DiscountCard;
import ru.clevertec.check.runner.streamIO.IStreamIO;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
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
    public List<DiscountCard> findAll() throws Exception {
        return (List) discountCardIO.importServiceFile();
    }

    @Override
    public DiscountCard add(DiscountCard o) throws Exception {
        increment++;
        //o.setId(super.createId(o));
        o.setId(increment);
        map.put(o.getId(),o);
        discountCardIO.exportFile(List.of(o),false);
        setFieldProperty();
        return o;
    }

    @Override
    public DiscountCard update(DiscountCard discountCard) throws Exception {
        //super.updateId(discountCard,discountCard.getId());
        map.put(discountCard.getId(),discountCard);
        return discountCard;
    }

    @Override
    protected void setProperty(Properties properties) {
        properties.setProperty("cardId", String.valueOf(increment));
    }
}
