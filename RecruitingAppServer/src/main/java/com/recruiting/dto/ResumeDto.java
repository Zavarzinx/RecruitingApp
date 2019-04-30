package com.recruiting.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recruiting.domain.Resume;
import com.recruiting.domain.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeDto extends Resume {
    private Long id;
    private User author;
    private String text;

    public Resume toResume(){
        Resume resume = new Resume();
        resume.setId(id);
        resume.setText(text);
        resume.setAuthor(author);
        return resume;
    }

    public static ResumeDto fromResume(Resume resume){
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setAuthor(resume.getAuthor());
        resumeDto.setId(resume.getId());
        resumeDto.setText(resume.getText());
        return resumeDto;
    }

}
