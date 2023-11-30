package com.sky.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

//    @Autowired
//    private SetmealDishMapper setmealDishMapper;

//    @Autowired
//    private SetmealMapper setmealMapper;

    /**
     * 新增菜品以及保存口味
     *
     * @param dishDTO
     */
    @Override
    @Transactional // 事务 要么都成功，要么都失败
    public void saveWithFlavor(DishDTO dishDTO) {
        // 保存菜品
        Dish dish = new Dish();
        log.info("dishDTO:{}", dishDTO);
        BeanUtils.copyProperties(dishDTO, dish);
        log.info("dish:{}", dish);
        dishMapper.insert(dish);
        // 获取菜品ID
        //获取insert语句生成的主键值
        Long dishId = dish.getId();
        // 保存口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            //给口味批量赋值菜品id
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult queryPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }



}
