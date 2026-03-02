package com.worthmate.demo.consultation.repository;

import com.worthmate.demo.consultation.entity.ConsultationEntity;
import com.worthmate.demo.util.ConsultationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity,Long> {
    List<ConsultationEntity> findByStudent_Id(Long studentId);

    List<ConsultationEntity> findByMentor_Id(Long mentorId);
    Long countByMentor_Id(Long mentorId);

    Long countByMentor_IdAndStatus(Long mentorId, ConsultationStatusEnum status);

    List<ConsultationEntity> findByMentor_IdAndStatus(
            Long mentorId,
            ConsultationStatusEnum status);

}
