package io.metersphere.testin.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsProjectTestinProjectTeamNameOrDescr extends MsProjectTestinProjectTeam{
    private String nameOrDescr;
}
