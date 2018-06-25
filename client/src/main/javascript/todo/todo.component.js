import "./todo.scss";

const TodoComponent = {
    template: require("./todo.html"),
    controller: TodoController,
    bindings: {
        todos: '<',
        addNew: '&'
    }
};

function TodoController() {
    let ctrl = this;
}

export {TodoComponent as default};