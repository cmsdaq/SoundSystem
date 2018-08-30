package ch.cern.cms.daq.soundsystem.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource
public interface SoundRecordRepository extends JpaRepository<SoundRecord, Long> {
    Optional<SoundRecord> findById(@Param("id") Long id);

}
