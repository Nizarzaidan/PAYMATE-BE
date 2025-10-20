package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("RewardJpaRepository")
public interface RewardJpaRepository extends JpaRepository<Reward, Integer> {
    List<Reward> findAllByOrderByIdRewardAsc();
    List<Reward> findByPoinLessThanEqualOrderByPoinAsc(Integer poin);
    List<Reward> findByPoinGreaterThanEqualOrderByPoinAsc(Integer poin);
}