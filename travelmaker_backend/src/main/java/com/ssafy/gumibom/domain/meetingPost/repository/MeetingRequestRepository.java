package com.ssafy.gumibom.domain.meetingPost.repository;

import com.ssafy.gumibom.domain.meetingPost.entity.MeetingRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingRequestRepository {

    private final EntityManager em;

    public void save(MeetingRequest req) {
        em.persist(req);
    }

    public void delete(MeetingRequest req) {
        em.remove(req);
    }

    public List<MeetingRequest> findSentByUserId(Long id) {
        return em.createQuery(
                "select mr from MeetingRequest mr "+
                        "where mr.requestor.id = :id "
                , MeetingRequest.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<MeetingRequest> findReceivedByUserId(Long id) {
        return em.createQuery(
                        "select mr from MeetingRequest mr "+
                                "where mr.acceptor.id = :id "
                        , MeetingRequest.class)
                .setParameter("id", id)
                .getResultList();
    }

}
