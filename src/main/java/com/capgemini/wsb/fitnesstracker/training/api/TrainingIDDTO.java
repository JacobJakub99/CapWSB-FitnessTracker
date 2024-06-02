package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TrainingIDDTO
{
    private Long id;
    private Long userId;
    private Date startTime;
    private Date endTime;
    private Double distance;
    private Double averageSpeed;
    private ActivityType activityType;
}