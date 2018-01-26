package main;

public class Scenario {

    private String id = "";
    private String crossover = "";
    private Double crossoverRatio = 0.0;
    private String mutation = "";
    private Double mutationRatio = 0.0;
    private String selection = "";
    private Boolean buildStatistics = true;
    private Boolean isEvaluated = true;
    private String evaluation = "";
    private Long maximumNumberOfEvaluations = 1000000000l;

    public Scenario() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public Double getCrossoverRatio() {
        return crossoverRatio;
    }

    public void setCrossoverRatio(Double crossoverRatio) {
        this.crossoverRatio = crossoverRatio;
    }

    public String getMutation() {
        return mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    public Double getMutationRatio() {
        return mutationRatio;
    }

    public void setMutationRatio(Double mutationRatio) {
        this.mutationRatio = mutationRatio;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
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

}
