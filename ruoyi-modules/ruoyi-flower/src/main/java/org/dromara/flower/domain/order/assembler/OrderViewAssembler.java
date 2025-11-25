package org.dromara.flower.domain.order.assembler;

import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;

// [MEILI-DOMAIN]: Order
/**
 * 订单视图装配器：负责将订单基础信息与会员、用户、地址等扩展信息进行视图层字段映射。
 */
public class OrderViewAssembler {

    public FolwerOrderVo buildOrderVo(FolwerOrderVo folwerOrderVo,
                                      AppletUserInformationVo appletUserInformationVo,
                                      MemberLevelVo memberLevelVo,
                                      FolwerPickAddrVo folwerPickAddrVo) {
        if (folwerOrderVo == null) {
            return null;
        }
        if (memberLevelVo != null) {
            folwerOrderVo.setMemberLevelName(memberLevelVo.getGradeName());
        }
        if (appletUserInformationVo != null) {
            folwerOrderVo.setUserName(appletUserInformationVo.getNickName());
            folwerOrderVo.setUserPhone(appletUserInformationVo.getPhone());
        }
        if (folwerPickAddrVo != null) {
            folwerOrderVo.setAddr(folwerPickAddrVo.getProvince() + folwerPickAddrVo.getCity() + folwerPickAddrVo.getArea() + folwerPickAddrVo.getAddr());
            folwerOrderVo.setMobile(folwerPickAddrVo.getMobile());
            folwerOrderVo.setAddrName(folwerPickAddrVo.getAddrName());
        }
        return folwerOrderVo;
    }
}
