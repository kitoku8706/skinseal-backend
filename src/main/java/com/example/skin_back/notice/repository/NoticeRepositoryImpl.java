package com.example.skin_back.notice.repository;

import com.example.skin_back.notice.entity.NoticeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<NoticeEntity> search(String keyword, String type, Pageable pageable) {
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            where.append(" AND (CAST(n.title AS TEXT) ILIKE ? OR CAST(n.content AS TEXT) ILIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        if (type != null && !type.isBlank()) {
            where.append(" AND n.type = ?");
            params.add(type);
        }

        String dataSql = "SELECT n.* FROM ss_notice n" + where.toString() + " ORDER BY n.created_at DESC";
        Query dataQuery = em.createNativeQuery(dataSql, NoticeEntity.class);
        for (int i = 0; i < params.size(); i++) {
            dataQuery.setParameter(i + 1, params.get(i));
        }
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        @SuppressWarnings("unchecked")
        List<NoticeEntity> content = (List<NoticeEntity>) dataQuery.getResultList();

        String countSql = "SELECT count(*) FROM ss_notice n" + where.toString();
        Query countQuery = em.createNativeQuery(countSql);
        for (int i = 0; i < params.size(); i++) {
            countQuery.setParameter(i + 1, params.get(i));
        }
        Number totalResult = ((Number) countQuery.getSingleResult());
        long total = totalResult == null ? 0L : totalResult.longValue();

        return new PageImpl<>(content, pageable, total);
    }
}