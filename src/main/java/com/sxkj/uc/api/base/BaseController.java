package com.sxkj.uc.api.base;

import com.sxkj.uc.entity.base.BaseEntity;
import com.sxkj.uc.util.CustomResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface BaseController<T extends BaseEntity> {
    CustomResult create(T t);
}
