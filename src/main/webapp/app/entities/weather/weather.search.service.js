(function() {
    'use strict';

    angular
        .module('weatherApp')
        .factory('WeatherSearch', WeatherSearch);

    WeatherSearch.$inject = ['$resource'];

    function WeatherSearch($resource) {
        var resourceUrl =  'api/_search/weathers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
