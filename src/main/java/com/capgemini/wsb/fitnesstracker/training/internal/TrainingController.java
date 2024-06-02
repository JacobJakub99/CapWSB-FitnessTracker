package com.capgemini.wsb.fitnesstracker.training.internal;


import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingIDDTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController
{
    private final TrainingProvider trainingProvider;
    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;
    private final TrainingServiceImpl trainingService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDTO createTraining(@RequestBody TrainingIDDTO trainingIdTO){
        return trainingMapper.toTraining(trainingService.createNewTraining(trainingIdTO));
    }
    @GetMapping
    public List<TrainingDTO> getTrainings() {
        return trainingProvider.getAllTrainings().stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public List<TrainingDTO> getTrainingsForUser(@PathVariable("userId") Long userId){
        return trainingService.getUserTrainings(userId).stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @GetMapping("/finished/{endDate}")
    public List<TrainingDTO> getTrainingsByEndTimeAfter(@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date endDate){
        return trainingRepository.findByEndTimeAfter(endDate).stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @GetMapping("/activityType")
    public List<TrainingDTO> getTrainingsByActivityType(@RequestParam("activityType") ActivityType activityType){
        return trainingRepository.findByActivityType(activityType).stream().map(trainingMapper::toTraining).collect(Collectors.toList());
    }

    @PutMapping("/{trainingId}")
    public TrainingDTO updateTraining(@PathVariable Long trainingId, @RequestBody TrainingIDDTO trainingIDDTO){
        Training training = trainingMapper.toEntityUpdate(trainingIDDTO);
        training.setId(trainingId);
        final Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toTraining(savedTraining);
    }
}
