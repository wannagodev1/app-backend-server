package org.wannagoframework.backend.repository.nosqldb;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 10/21/19
 */
public interface DefaultTranslationRepository<T> extends JpaRepository<T, Long> {
  Optional<T> getByRelatedGraphDbIdAndIso3Language(Long relatedGraphDbId, String iso3Language);
  Optional<T> getByRelatedGraphDbIdAndIsDefaultTrue(Long relatedGraphDbId);

  List<T> getByRelatedGraphDbId(Long relatedGraphDbId);
}
