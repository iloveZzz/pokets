package com.xianbei.pocket.dao.typehandler;

import com.xianbei.pocket.entity.ItravelApiBusiness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhudaoming on 2017/4/19.
 */
@Mapper
public interface ItravelApiBusMapper {
    @Select("select id,api_business_id apiBusinessId from itravel_api_business where type=#{type}")
    public List<ItravelApiBusiness> getItravelApiBusList(String type);
    @Select("select m.id,m.api_business_id from itravel_api_business m where m.type = #{type} and m.api_business_id not in(select api_course_id from itravel_api_business_model_course group by api_course_id)")
    public List<ItravelApiBusiness> getErrorApiBusList(String type);
}
