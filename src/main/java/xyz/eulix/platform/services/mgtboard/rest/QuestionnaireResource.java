package xyz.eulix.platform.services.mgtboard.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import xyz.eulix.platform.services.mgtboard.dto.*;
import xyz.eulix.platform.services.mgtboard.service.QuestionnaireService;
import xyz.eulix.platform.services.support.log.Logged;
import xyz.eulix.platform.services.support.model.PageListResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Questionnaire Rest类
 */
@ApplicationScoped
@Path("/v1/api")
@Tag(name = "Platform Questionnaire Management Service", description = "提供问卷相关接口.")
@Tag(name = "0.5.0")
public class QuestionnaireResource {
    private static final Logger LOG = Logger.getLogger("app.log");

    @Inject
    QuestionnaireService questionnaireService;

    @POST
    @Path("/questionnaire")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Logged
    @Operation(description = "新增问卷")
    public QuestionnaireRes questionnaireSave(@NotBlank @Parameter(required = true) @HeaderParam("Request-Id") String requestId,
                                   @Valid QuestionnaireReq questionnaireReq) {
        return questionnaireService.saveQuestionnaire(questionnaireReq);
    }

    @PUT
    @Path("/questionnaire/{questionnaire_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Logged
    @Operation(description = "更新问卷")
    public QuestionnaireRes questionnaireUpdate(@NotBlank @Parameter(required = true) @HeaderParam("Request-Id") String requestId,
                                     @NotNull @Parameter(required = true) @PathParam("questionnaire_id") Long questionnaireId,
                                     @Valid QuestionnaireUpdateReq updateReq) {
        return questionnaireService.updateQuestionnaire(questionnaireId, updateReq);
    }

    @DELETE
    @Path("/questionnaire/{questionnaire_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Logged
    @Operation(description = "删除问卷")
    public BaseResultRes questionnaireDel(@NotBlank @Parameter(required = true) @HeaderParam("Request-Id") String requestId,
                                    @NotNull @PathParam("questionnaire_id") Long questionnaireId) {
        questionnaireService.deleteQuestionnaire(questionnaireId);
        return BaseResultRes.of(true);
    }

    @GET
    @Path("/questionnaire/{questionnaire_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Logged
    @Operation(description = "查询问卷详情")
    public QuestionnaireRes questionnaireGet(@NotBlank @Parameter(required = true) @HeaderParam("Request-Id") String requestId,
                                    @NotNull @Parameter(required = true) @PathParam("questionnaire_id") Long questionnaireId) {
        return questionnaireService.getQuestionnaire(questionnaireId);
    }

    @GET
    @Path("/questionnaire/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Logged
    @Operation(description = "获取问卷列表")
    public PageListResult<QuestionnaireRes> questionnaireList(@NotBlank @Parameter(required = true) @HeaderParam("Request-Id") String requestId,
                                      @Parameter(description = "用户域名") @QueryParam("subdomain") String subdomain,
                                      @Parameter(required = true, description = "当前页") @QueryParam("current_page") Integer currentPage,
                                      @Parameter(required = true, description = "每页数量，最大1000") @Max(1000) @QueryParam("page_size") Integer pageSize) {
        return questionnaireService.listQuestionnaire(subdomain, currentPage, pageSize);
    }

    @POST
    @Path("/questionnaire/feedback")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Logged
    @Operation(description = "用户提交问卷")
    public FeedbackRes feedbackSave(@HeaderParam("Request-Id") String requestId, @Valid FeedbackReq feedbackReq) {
        return questionnaireService.feedbackSave(feedbackReq);
    }
}
