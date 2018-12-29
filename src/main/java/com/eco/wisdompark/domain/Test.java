package com.eco.wisdompark.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.oval.constraint.Length;

/**
 * <p>
 * 
 * </p>
 *
 * @author litao
 * @since 2018-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Length(min = 10, max = 20, message = "设备名称在10到20个字符之间")
    private String name;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
