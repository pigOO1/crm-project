package com.powernode.crm.workbench.mapper;

import com.powernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbg.generated Tue Sep 12 20:42:27 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbg.generated Tue Sep 12 20:42:27 CST 2023
     */
    int insert(Clue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbg.generated Tue Sep 12 20:42:27 CST 2023
     */
    int insertSelective(Clue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbg.generated Tue Sep 12 20:42:27 CST 2023
     */
    Clue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbg.generated Tue Sep 12 20:42:27 CST 2023
     */
    int updateByPrimaryKeySelective(Clue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue
     *
     * @mbg.generated Tue Sep 12 20:42:27 CST 2023
     */
    int updateByPrimaryKey(Clue row);

    /**
     * 保存线索
     * @param clue
     * @return
     */
    int insertClue(Clue clue);

    /**
     * 分页查询
     * @param map
     * @return
     */
    List<Clue> selectClueByConditionForPage(Map<String,Object> map);

    /**
     * 查询线索总数
     * @param map
     * @return
     */
    int selectCountOfClueByCondition(Map<String, Object> map);

    /**
     * 根据id查询线索明细
     * @param id
     * @return
     */
    Clue selectClueForDetailById(String id);

    /**
     * 根据id查询clue
     * @param id
     * @return
     */
    Clue selectClueById(String id);

    /**
     * 删除该线索
     * @param id
     * @return
     */
    int deleteClueById(String id);
}