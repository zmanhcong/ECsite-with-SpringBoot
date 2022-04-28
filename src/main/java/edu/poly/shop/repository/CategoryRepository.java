package edu.poly.shop.repository;

import edu.poly.shop.domain.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    List<Category> findAll();

    @Override
    List<Category> findAll(Sort sort);

    @Override
    Page<Category> findAll(Pageable pageable);

    @Override
    List<Category> findAllById(Iterable<Long> longs);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Category entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Category> entities);

    @Override
    void deleteAll();

    @Override
    <S extends Category> S save(S entity);

    @Override
    <S extends Category> List<S> saveAll(Iterable<S> entities);

    @Override
    Optional<Category> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    void flush();

    @Override
    <S extends Category> S saveAndFlush(S entity);

    @Override
    <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Category> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Category getOne(Long aLong);

    @Override
    Category getById(Long aLong);

    @Override
    <S extends Category> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Category> List<S> findAll(Example<S> example);

    @Override
    <S extends Category> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Category> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Category> long count(Example<S> example);

    @Override
    <S extends Category> boolean exists(Example<S> example);

    @Override
    <S extends Category, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
