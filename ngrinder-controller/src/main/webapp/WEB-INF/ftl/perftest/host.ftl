<div class="div-host" 
	rel='popover' 
	title='<@spring.message "perfTest.configuration.targetHost"/>' 
	data-html='true'
	data-content='<@spring.message "perfTest.configuration.targetHost.help"/>' 
	data-placement='bottom'>
</div>
<input type="hidden" name="targetHosts" id="target_hosts" value="${(targetHosts)!}">
<a class="btn pull-right btn-mini add-host-btn" data-toggle="modal" href="#add_host_modal"> 
	<@spring.message "perfTest.configuration.add"/> 
</a>