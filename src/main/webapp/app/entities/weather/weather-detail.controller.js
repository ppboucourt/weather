(function() {
    'use strict';

    angular
        .module('weatherApp')
        .controller('WeatherDetailController', WeatherDetailController);

    WeatherDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Weather'];

    function WeatherDetailController($scope, $rootScope, $stateParams, previousState, entity, Weather) {
        var vm = this;

        vm.weather = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weatherApp:weatherUpdate', function(event, result) {
            vm.weather = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
