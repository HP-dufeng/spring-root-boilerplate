package example.service.dto;


public class OptionCount {
    private Long optionId;
    private int count;

    public Long getOptionId(){return this.optionId;}
    public void setOptionId(Long optionId) {this.optionId = optionId;}

    public int getCount(){return this.count;}
    public void setCount(int count){this.count=count;}
}
