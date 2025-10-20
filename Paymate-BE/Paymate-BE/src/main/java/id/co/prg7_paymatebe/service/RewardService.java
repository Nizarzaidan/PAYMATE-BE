package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.RewardJpaRepository;
import id.co.prg7_paymatebe.vo.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class RewardService {

    @Qualifier("RewardJpaRepository")
    @Autowired
    private RewardJpaRepository mRewardJpaRepository;

    public Reward getReward(Integer id) {
        return mRewardJpaRepository.findById(id).orElse(null);
    }

    public List<Reward> getRewards() {
        return mRewardJpaRepository.findAllByOrderByIdRewardAsc();
    }

    public List<Reward> getRewardsByPoinMax(Integer maxPoin) {
        return mRewardJpaRepository.findByPoinLessThanEqualOrderByPoinAsc(maxPoin);
    }

    public List<Reward> getRewardsByPoinMin(Integer minPoin) {
        return mRewardJpaRepository.findByPoinGreaterThanEqualOrderByPoinAsc(minPoin);
    }

    public Reward saveReward(Reward reward) {
        System.out.println(">>> Saving Reward: " + reward);
        return mRewardJpaRepository.save(reward);
    }

    public boolean updateReward(Reward reward) {
        Reward result = mRewardJpaRepository.findById(reward.getIdReward()).orElse(null);
        if (result == null) return false;

        if (StringUtils.hasLength(reward.getNamaReward())) {
            result.setNamaReward(reward.getNamaReward());
        }
        if (reward.getKriteria() != null) {
            result.setKriteria(reward.getKriteria());
        }
        if (reward.getPoin() != null) {
            result.setPoin(reward.getPoin());
        }
        if (reward.getDeskripsi() != null) {
            result.setDeskripsi(reward.getDeskripsi());
        }

        mRewardJpaRepository.save(result);
        return true;
    }

    public boolean deleteReward(Integer id) {
        Reward result = mRewardJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        mRewardJpaRepository.delete(result);
        return true;
    }
}