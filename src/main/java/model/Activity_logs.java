package model;

import lombok.*;

@Data
@ToString@AllArgsConstructor@NoArgsConstructor


public class Activity_logs {
    Integer logId;
    Integer userId;
    String description;
    String timeStamp;
}
