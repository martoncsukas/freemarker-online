<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
    
    <script src="https://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="js/jquery.blockUI.js"></script>
    <script src="js/autosize.min.js"></script>
    <script src="js/script.js"></script>
    <script>
        $(function() {
            // Auto-focus on first form input:
            $('#templateAndModelForm *:input[type!=hidden]:first').focus();
            
            // Submit form when Ctrl+Enter is hit in a textarea:            

        
            // Dynamically adapt text areas heights to their content:
            //$('#templateAndModelForm textarea').autosize();
            autosize($('textarea'));
        
            // Show/hide data model examples:
            $("#showHideDataModelExamples").click(function(e) {
            	 $("#dataModelExamples").toggle();
           		 $("#hideDataModelExamplesLabel").toggle();
                 $("#showDataModelExamplesLabel").toggle();
            	 
                 e.preventDefault();
            	 return false;
            })
            <#if execute>
                execute();
            </#if>
        });
    </script>
    
    <title>FreeMarker Expression Tester</title>
</head>
<body background="http://www.mobizio.com/images/login-mobizio-bg.jpg" style="background-size: cover">
<div id="layout">
    <div id="main">
        <div class="header">
            <h1 style="font-weight: bold; color:white; font-size: 35px">FreeMarker Expression Tester</h1>
        </div>

        <div class="content">
            <form id="templateAndModelForm" method="post" class="pure-form pure-form-stacked">
                <label for="template" style="color:white">Expression <span class="faint">(FreeMarker ${freeMarkerVersion})</span></label>
                <textarea id="template" name="template" class="pure-input-1 source-code"
                        placeholder="Enter expression, like: Hello ${r'${form_component_1}'}!"
                >${template}</textarea>
    
                <label for="template" style="color:white">
                    Data model components
                    (<a id="showHideDataModelExamples" href="#" style="color:#bbb" tabindex="-1"><!--
                    --><span id="showDataModelExamplesLabel">show</span><!--
                    --><span id="hideDataModelExamplesLabel" class="hiddenByDefault">hide</span>
                    examples</a>)
                </label>
                <pre id="dataModelExamples" class="hiddenByDefault">form_component_1 = Some value
form_component_2 = "JSON\nsyntax"
someNumber = 3.14
someBoolean = true
someDate = 2014-02-28
someTime = 20:50:30.5+02:00
someDatetime = 2014-02-28T18:50Z
someList = ["JSON", "syntax", 1, 2, 3 ]
someMap = { "JSON syntax": true, "nestedList": [1, 2, 3] }
someXML = &lt;example x="1"&gt;text&lt;/example&gt;</pre>
                <textarea id="dataModel" name="dataModel" class="pure-input-1 source-code"
                        placeholder='Enter one or more assignments (e.g., form_component_1 = John Doe), starting each in its own line.'
                >${dataModel}</textarea>
                <span id="error" class="errorMessage">Template cannot be empty</span>
                <div class="formBottomButtonsContainer">
	                <input id="eval-btn" type="button" value="Evaluate" class="pure-button pure-button-primary"/>
	                &nbsp; <span class="faint">Ctrl+Enter in input fields will submit this form too</span>
                </div>
                <div style="display:none" class="resultContainer">
                    <label for="result" style="color:white">Result</label>
                    <textarea id="result" class="pure-input-1 source-code" readonly></textarea>
                </div>

            </form>
        </div><!-- content -->
        
        <div class="footer" style="color: #bbb">
            FreeMarker documentation:
            <a href="http://freemarker.org/docs/" target="_blank" style="color: #bbb">Contents</a>
            |
            <a href="http://freemarker.org/docs/dgui_template_overallstructure.html" target="_blank" style="color: #bbb">Overall&nbsp;syntax</a>
            |
            <a href="http://freemarker.org/docs/dgui_template_exp.html#exp_cheatsheet" target="_blank" style="color: #bbb">Expression&nbsp;syntax</a>
            |
            <a href="http://freemarker.org/docs/ref_directive_alphaidx.html" target="_blank" style="color: #bbb">List&nbsp;of&nbsp;&lt;#<i>directives</i>&gt;</a>
            |
            <a href="http://freemarker.org/docs/ref_builtins_alphaidx.html" target="_blank" style="color: #bbb">List&nbsp;of&nbsp;<tt>?<i>built_in</i></tt> functions</a>
        </div><!-- footer -->
    </div><!-- main -->
</div><!-- layout -->
</body>
</html>
</#escape>