<div class="navbar navbar-inverse navbar-fixed-top" style="background-color: black;">   
    <div class="main_header" style="padding-left: 0px;padding-right: 0px;">
        <div class="row" style="padding-left: 0px;padding-right: 0px;">

            <div class="col-sm-3">
                <img class="logo" style="padding-left: 0px;padding-right: 0px;padding-top: 0px;padding-bottom: 0px;" alt="ntb logo" src="resouces/images/ntb.png" />               
            </div>

            <div class="col-sm-8">
                <div class="row">
                    <div  class="col-sm-9" style="padding-right: 0px;">	
                        <h5 style="font-family:Arial,Helvetica,sans-serif;font-size: 10pt;color: #e6b800;">Welcome,&nbsp; ${SYSTEMUSER.username} | User Role: ${SYSTEMUSER.userrole.description} | ${CURRENTDATE} |Last login date time: ${LOGGEDDATE}</h5>						
                    </div>

                </div>
            </div>
            <div class="col-sm-1">
                <div class="dropdown pull-right" style="margin-top: 16px;font-family:Lucida Grande,Lucida Sans,Arial,sans-serif;">
                    <button class="dropdown-toggle btn-sm" type="button" data-toggle="dropdown" style="background-color: white;border-color:#e6b800;border-width:0.5pt;border-style: double;">
                        <span class="caret" style="color:#908e8e;"></span>
                    </button>
                    <ul class="dropdown-menu" style="background-color: black;border-color:#e6b800; ">
                        <li role="separator" class="divider" style="background-color: #e6b800;"></li>
                        <li>
                            <a class="btn btn-sm" href="LogoutUserLogin.action?message=error3">
                                <h6 class="text-left" style="color: #e6b800;"> <span class="glyphicon glyphicon-off" style="color: #e6b800;"></span> &nbsp;Logout</h6>
                            </a>
                        </li>                                                             
                    </ul>
                </div>
            </div>
        </div>
    </div> 
    <div style=" background: #e6b800;
         background: radial-gradient(#e6b800, #e6b800);
         border-radius: 6px;
         height: 4px;">
    </div>
</div>
