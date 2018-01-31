<style>
    .ntb_navbar{
        padding: 10px 45px 15px 15px;;
        position: relative;
        height: 31px;
        font-size: 13px;
        margin-top: 10px;
        margin-left: -15px;
        font-weight: bold;
        /*color: #0080C7;*/
        color: #c14900;
        list-style-type:none;
    }
</style>
<div class="row">
    <div class="col-sm-12">
        <ul class="ntb_navbar">
            <li>
            <h> ${CURRENTSECTION}</h> 
                <% if (session.getAttribute("CURRENTSECTION") != null && !session.getAttribute("CURRENTSECTION").equals("")) {%>
            &nbsp;<b>&raquo;</b>&nbsp;
            <% }%>
            <h>${CURRENTPAGE}</h>
            </li>				
        </ul>
    </div>
</div>