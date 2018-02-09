<!-- 字典实体配置信息编辑 start -->
<div id="dictEntityModifyContent" class="hidden">
    <form class="form-horizontal" id="dictEntityModifyForm" class="form-horizontal" method="post">
        <div class="row">
            <div class="control-group span25">
                <label class="control-label"><s>*</s>字典类型：</label>
                <div class="controls">
					<input name="id" value="" type="hidden">
                    <select name="typeCode" class="input-large control-text" readonly="true">
                        <option value="" selected>--请选择字典类型</option>
						<#if dictTypeVOList?? && dictTypeVOList?size!=0>
							<#list dictTypeVOList as ddVO>
								<option value="${ddVO.code}">${ddVO.name}</option>
							</#list>
						</#if>
                    </select>
                    <div class="readonly" style="width:385px;"></div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span25">
                <label class="control-label"><s>*</s>实体名称：</label>
                <div class="controls">
                    <input name="name" type="text" data-rules="{required:true}"
                           data-messages="{required:'请输入实体名称'}" class="input-large control-text" maxlength="30">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span25">
                <label class="control-label"><s>*</s>实体内容：</label>
                <div class="controls">
                    <input name="content" type="text" data-rules="{required:true}"
                           data-messages="{required:'请输入实体内容'}" class="input-large control-text" maxlength="30" readonly="true">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span25">
                <label class="control-label"><s>*</s>实体排序：</label>
                <div class="controls">
                    <input name="sort" type="text" data-rules="{required:true,number:true}"
                           data-messages="{required:'请输入实体排序'}" class="input-large control-text" maxlength="3">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span25">
                <label class="control-label"><s>*</s>状态：</label>
                <div class="controls">
                    <select name="status" class="input-large" data-rules="{required:true}" data-messages="{required:'请选择字典实体状态'}">
                        <option value="" selected>--请选择字典实体状态</option>
                        <option value="US" selected>使用</option>
                        <option value="AB" selected>作废</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="control-group span25">
                <label class="control-label">描述：</label>
                <div class="controls">
                    <textarea name="remark" class="input-large control-text" data-rules="{maxlength:300}"></textarea>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- 字典实体配置信息编辑 end -->