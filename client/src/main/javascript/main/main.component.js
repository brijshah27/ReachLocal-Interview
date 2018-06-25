import "./main.scss";

const MainComponent = {
    template: require("./main.html"),
    controller: MainController,
    bindings: {}
};

MainController.$inject = ['todoService'];

function MainController(todoService) {
    let ctrl = this;
    ctrl.$onInit = function () {
        ctrl.todos = todoService.getTodos();
    };

    ctrl.handleAddNew = function (description) {
        ctrl.todos.push({description: description});
    }
}

export {MainComponent as default};