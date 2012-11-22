templateTestParser = function(template, data) {
    var myTemplateObj = TrimPath.parseTemplate(template);
    return myTemplateObj.process(data);;
}
