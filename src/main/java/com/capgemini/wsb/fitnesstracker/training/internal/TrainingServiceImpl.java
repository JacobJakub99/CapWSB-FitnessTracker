package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingIDDTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider
{

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    private final TrainingMapper trainingMapper;
    @Override
    public Optional<User> getTraining(final Long trainingId)
    {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> getAllTrainings()
    {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getUserTrainings(Long UserId)
    {
        return trainingRepository.findByUserId(UserId);
    }

    public Training createNewTraining(TrainingIDDTO trainingIDDTO)
    {
        User user = userProvider.getUser(trainingIDDTO.getUserId()).get();
        Training training = trainingMapper.toEntity(trainingIDDTO);
        training.setUser(user);
        trainingRepository.save(training);
        return training;
    }

}
