package com.ibm.sso.service;

import com.aef3.data.Filter;
import com.aef3.data.api.DomainDto;
import com.aef3.data.api.DomainEntity;
import com.aef3.data.api.GenericEntityDAO;
import com.aef3.data.api.qbe.CompareObject;
import com.aef3.data.api.qbe.RangeObject;
import com.aef3.data.api.qbe.SortObject;
import com.aef3.data.api.qbe.StringSearchType;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Generated By AEF3 Framework, powered by Dr.Adldoost :D 
 * @param <E>
 * @param <PK>
 */

public abstract class GeneralServiceImpl<D extends DomainDto, E extends DomainEntity, PK extends Serializable> implements GeneralService<D , PK> {

    protected abstract GenericEntityDAO getDAO();

    @Override
    @Transactional
    public D save(D entity) {
        return (D)entity.getInstance(getDAO().save(entity.toEntity()));
    }

    @Override
    @Transactional
    public void save(List<D> dtos) {
        if(dtos == null)
            return;
        List<E> entities = new ArrayList<>();
        dtos.forEach(d -> {
            entities.add((E)d.toEntity());
        });
        getDAO().save(entities);
    }

    @Override
    @Transactional
    public void remove(PK id) {
        getDAO().remove(id);
    }

    @Override
    @Transactional
    public void bulkRemove(List<PK> entityIdList) {
        getDAO().bulkRemove(entityIdList);
    }

    @Override
    public List<D> findByExample(D example, List<SortObject> sortObjectList, int startIndex, int pageSize, StringSearchType searchType) {
        if(example == null)
            return null;
        List<E> entities = new ArrayList<>();
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), sortObjectList, startIndex, pageSize, searchType));
    }

    @Override
    public List<D> findByExample(D example, int startIndex, int pageSize, StringSearchType searchType) {
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), startIndex, pageSize, searchType));
    }

    @Override
    public List<D> findByExample(D example) {
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity()));
    }

    @Override
    public List<D> findByExample(D example, List<SortObject> sortObjectList) {
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), sortObjectList));
    }

    @Override
    public List<D> findByExample(D example, List<SortObject> sortObjectList, StringSearchType searchType) {
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), sortObjectList, searchType));
    }

    @Override
    public List<D> findByExample(D example, List<SortObject> sortObjectList, int startIndex, int pageSize, StringSearchType searchType, List<RangeObject> rangeObjectList){
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), sortObjectList, startIndex, pageSize, searchType, rangeObjectList, null));
    }

    @Override
    public List<D> findByExample(D example, List<SortObject> sortObjectList, int startIndex, int pageSize, StringSearchType searchType, List<RangeObject> rangeObjectList, List<CompareObject> comparableList){
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), sortObjectList, startIndex, pageSize, searchType, rangeObjectList, comparableList));
    }

    @Override
    public List<D> findByExample(D example, List<SortObject> sortObjectList, StringSearchType searchType, List<RangeObject> rangeObjectList) {
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), sortObjectList, searchType, rangeObjectList));
    }

    @Override
    public List<D> findByExample(D example, StringSearchType searchType) {
        if(example == null)
            return null;
        return getDtoInstance().getInstance(getDAO().findByExample(example.toEntity(), searchType));
    }

    @Override
    public D findSingleByExample(D example, List<SortObject> sortObjectList, StringSearchType searchType) {

        if(example == null)
            return null;
        return (D)getDtoInstance().getInstance(getDAO().findSingleByExample(example.toEntity(), sortObjectList, searchType));

    }

    @Override
    public D findSingleByExample(D example, List<SortObject> sortObjectList) {
        if(example == null)
            return null;
        return (D)getDtoInstance().getInstance(getDAO().findSingleByExample(example.toEntity(), sortObjectList));
    }

    @Override
    public D findSingleByExample(D example, StringSearchType searchType) {
        if(example == null)
            return null;
        return (D)getDtoInstance().getInstance(getDAO().findSingleByExample(example.toEntity(), searchType));
    }

    @Override
    public D findSingleByExample(D example) {
        if(example == null)
            return null;
        return (D)getDtoInstance().getInstance(getDAO().findSingleByExample(example.toEntity()));
    }

    @Override
    public long countByExample(D example, StringSearchType searchType) {
        if(example == null)
            return 0;
        return getDAO().countByExample(example.toEntity(), searchType);
    }

    @Override
    @Transactional
    public void removeByExample(D example, StringSearchType searchType) {
        if(example == null)
            return;
        getDAO().removeByExample(example.toEntity(), searchType);
    }

    @Override
    public D findByPrimaryKey(PK primaryKey) {
        if(primaryKey == null)
            return null;
        E entity = (E)getDAO().findByPrimaryKey(primaryKey);
        if(entity == null)
            return null;
        return (D)getDtoInstance().getInstance(entity);
    }

    public List<D> paginate(Filter<D> filter) {

        throw new RuntimeException("NOT IMPLEMENTED...");
    }

    @Override
    public abstract D getDtoInstance();
    @Override
    public Long countByExample(D example, StringSearchType searchType, List<RangeObject> rangeObjectList, List<CompareObject> compareObjects) {
        if(example == null)
            return 0L;
        return getDAO().countByExample(example.toEntity(), searchType, rangeObjectList, compareObjects);
    }

    @Override
    public PagedResult<D> findPagedByExample(D example, List<SortObject> sortObjectList, int startIndex, int pageSize, StringSearchType searchType, List<RangeObject> rangeObjectList, List<CompareObject> compareObjects) {
        List<D> data = findByExample(example, sortObjectList, startIndex, pageSize, searchType, rangeObjectList, compareObjects);
        Long count = countByExample(example, searchType, rangeObjectList, compareObjects);
        PagedResult<D> pagedResult = new PagedResult<>();
        pagedResult.setData(data);
        pagedResult.setCount(count);
        return pagedResult;
    }}
