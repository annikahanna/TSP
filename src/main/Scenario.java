package main;

import crossover.ICrossover;
import mutation.IMutation;
import selection.ISelection;

public class Scenario {

    private String id = "";
    private String evaluation = "";
    private Long maximumNumberOfEvaluations = 1000000000l;
    private Double crossoverRatio = 0.0;
    private Double mutationRatio = 0.0;
    private Boolean buildStatistics = true;
    private Boolean isEvaluated = true;
    private ICrossover crossover;
    private ISelection selection;
    private IMutation mutation;

    public Scenario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Long getMaximumNumberOfEvaluations() {
        return maximumNumberOfEvaluations;
    }

    public void setMaximumNumberOfEvaluations(Long maximumNumberOfEvaluations) {
        this.maximumNumberOfEvaluations = maximumNumberOfEvaluations;
    }

    public Double getCrossoverRatio() {
        return crossoverRatio;
    }

    public void setCrossoverRatio(Double crossoverRatio) {
        this.crossoverRatio = crossoverRatio;
    }

    public Double getMutationRatio() {
        return mutationRatio;
    }

    public void setMutationRatio(Double mutationRatio) {
        this.mutationRatio = mutationRatio;
    }

    public Boolean getBuildStatistics() {
        return buildStatistics;
    }

    public void setBuildStatistics(Boolean buildStatistics) {
        this.buildStatistics = buildStatistics;
    }

    public Boolean getEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        isEvaluated = evaluated;
    }

    public ICrossover getCrossover() {
        return crossover;
    }

    public void setCrossover(ICrossover crossover) {
        this.crossover = crossover;
    }

    public ISelection getSelection() {
        return selection;
    }

    public void setSelection(ISelection selection) {
        this.selection = selection;
    }

    public IMutation getMutation() {
        return mutation;
    }

    public void setMutation(IMutation mutation) {
        this.mutation = mutation;
    }

}
