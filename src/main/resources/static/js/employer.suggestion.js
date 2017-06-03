$(document).ready(function () {
    $('.employer-search-input').autocomplete({
        serviceUrl: "/webservice/employer-suggestions.json",
        minChars: 4,
        showNoSuggestionNotice: true,
        noSuggestionNotice: "No results. Enter name to create new employer",
        onSelect: function (suggestion) {
            $.get("/webservice/employer-info.json", {name: suggestion.value}, function(data, status) {                
                $('input[name="employer.city"]').val(data.city);
                $('input[name="employer.state"]').val(data.state);                
            });
        },
    });
    
    $('input#employed').on('changed', function(evt) {
        alert('changed');
    });
});

