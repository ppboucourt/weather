(function() {
    'use strict';

    angular
        .module('weatherApp')
        .controller('WeatherDeleteController',WeatherDeleteController);

    WeatherDeleteController.$inject = ['$uibModalInstance', 'entity', 'Weather'];

    function WeatherDeleteController($uibModalInstance, entity, Weather) {
        var vm = this;

        vm.weather = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Weather.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
