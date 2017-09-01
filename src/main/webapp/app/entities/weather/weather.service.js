(function() {
    'use strict';
    angular
        .module('weatherApp')
        .factory('Weather', Weather);

    Weather.$inject = ['$resource'];

    function Weather ($resource) {
        var resourceUrl =  'api/weathers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'cities': {url: 'api/weather/cities/:cities', method: 'GET', isArray: true}
        });
    }
})();
