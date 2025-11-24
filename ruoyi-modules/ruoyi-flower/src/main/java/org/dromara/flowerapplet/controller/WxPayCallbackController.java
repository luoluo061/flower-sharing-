package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.wechat.pay.java.service.payments.model.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.HttpStatus;
import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/wxpayback")
// [MEILI-DOMAIN]: Payment
public class WxPayCallbackController {

    private final IPayService payService;

    private final IFolwerAppletOrderService folwerAppletOrderService;

    /***
     * 微信小程序支付回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    /**
     * Phase 1 cross-domain touchpoint.
     * 支付回调后调用 Order 领域订单服务完成支付确认与订单状态更新。
     *
     * 调用领域：Order
     * 注意：当前仅作为 Phase 1 关注点标记，暂不调整具体实现。
     */
    // TODO [Phase1] Payment → Order 跨领域依赖，后续按 MEILI-CENTER 设计文档梳理边界。
    @SaIgnore
    @PostMapping("/pay/payCallback")
    public R<FolwerAppletOrderVo> callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object ret = payService.confirmOrder(request,response);
            FolwerAppletOrderVo folwerAppletOrderVo = folwerAppletOrderService.payCallbackOrder((Transaction) ret);
            return R.ok("操作成功",folwerAppletOrderVo);
        } catch (Exception ex) {
            return R.fail("操作失败", null);
        }
    }

    /***
     * 微信小程序退款回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SaIgnore
    @PostMapping("/pay/refundCallback")
    public R<String> refundCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object ret = payService.refundNotify(request);
            return R.ok("操作成功",ret.toString());
        } catch (Exception ex) {
            return R.fail("操作失败", ex.getLocalizedMessage());
        }
    }
}
