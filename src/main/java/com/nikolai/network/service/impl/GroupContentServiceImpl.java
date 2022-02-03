package com.nikolai.network.service.impl;

import com.nikolai.network.dto.ContentGroupDto;
import com.nikolai.network.model.ContentGroup;
import com.nikolai.network.model.Group;
import com.nikolai.network.repository.GroupContentRepository;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupContentServiceImpl extends BaseServiceImpl{

    @Autowired
    private GroupContentRepository contentRepository;

    @Autowired
    private DtoConvert dtoConvert;

    public void saveGroupContent(ContentGroup contentGroup) {
        contentGroup.setDate(dateNow());
        contentRepository.save(contentGroup);
    }

    public List<ContentGroupDto> listContentForGroup(Group group){

        List<ContentGroupDto> list = new ArrayList<>();

        for (ContentGroup q : contentRepository.findByGroup(group)){
            list.add(dtoConvert.convertToContentDto(q));
        }

        list.sort((d2,d1)->d1.getTimeCompare().compareTo(d2.getTimeCompare()));

        return list;

    }

 }
