package com.sxkj.uc.service.base;

import com.sxkj.uc.entity.base.BaseEntity;
import org.springframework.stereotype.Service;


public interface BaseService<T extends BaseEntity> {
    T create(T t);
}
