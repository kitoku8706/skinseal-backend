package com.example.skin_back.notice.repository;

import com.example.skin_back.notice.entity.NoticeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<NoticeEntity> search(String keyword, String type, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT n FROM NoticeEntity n WHERE 1=1");

        if (keyword != null && !keyword.isEmpty()) {
            queryBuilder.append(" AND (LOWER(n.title) LIKE LOWER(:keyword) OR LOWER(n.content) LIKE LOWER(:keyword))");
        }
        if (type != null && !type.isEmpty()) {
            queryBuilder.append(" AND n.type = :type");
        }

        queryBuilder.append(" ORDER BY n.createdAt DESC");

        TypedQuery<NoticeEntity> query = entityManager.createQuery(queryBuilder.toString(), NoticeEntity.class);

        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (type != null && !type.isEmpty()) {
            query.setParameter("type", type);
        }

        // Count query for pagination
        StringBuilder countQueryBuilder = new StringBuilder("SELECT COUNT(n) FROM NoticeEntity n WHERE 1=1");
        if (keyword != null && !keyword.isEmpty()) {
            countQueryBuilder.append(" AND (LOWER(n.title) LIKE LOWER(:keyword) OR LOWER(n.content) LIKE LOWER(:keyword))");
        }
        if (type != null && !type.isEmpty()) {
            countQueryBuilder.append(" AND n.type = :type");
        }

        TypedQuery<Long> countQuery = entityManager.createQuery(countQueryBuilder.toString(), Long.class);
        if (keyword != null && !keyword.isEmpty()) {
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }
        if (type != null && !type.isEmpty()) {
            countQuery.setParameter("type", type);
        }
        long totalRows = countQuery.getSingleResult();


        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<NoticeEntity> resultList = query.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
    }
}
