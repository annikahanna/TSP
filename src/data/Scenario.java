package data;

public class Scenario {
    private String crossover;
    private String mutation;
    private String selection;
    private String evaluation;
    private String id;
    private Long maximumNumberOfEvaluations;
    private Double crossoverRatio;
    private Double mutationRatio;
    private Boolean buildStatistics;
    private Boolean isEvaluated;

    public Scenario(String crossover, String mutation, String selection, String evaluation, String id, Long maximumNumberOfEvaluations, Double crossoverRatio, Double mutationRatio, Boolean buildStatistics, Boolean isEvaluated) {
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
        this.evaluation = evaluation;
        this.id = id;
        this.maximumNumberOfEvaluations = maximumNumberOfEvaluations;
        this.crossoverRatio = crossoverRatio;
        this.mutationRatio = mutationRatio;
        this.buildStatistics = buildStatistics;
        this.isEvaluated = isEvaluated;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public String getMutation() {
        return mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
