templateTestParser = function(template, data) {
    var pagefn = doT.template(template);
    return pagefn(data);
}
