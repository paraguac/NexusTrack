import React from 'react';
import _ from 'lodash';

export interface Task {
  id: number;
  projectId: number;
  title: string;
  assignee: string;
  priority: string;
  dueDate: string;
}

interface TaskBoardProps {
  tasks: Task[];
}

const priorityOrder: Record<string, number> = {
  HIGH: 1,
  MEDIUM: 2,
  LOW: 3,
};

export const TaskBoard: React.FC<TaskBoardProps> = ({ tasks }) => {
  const sorted = _.sortBy(tasks, (task) => priorityOrder[task.priority] || 99);

  return (
    <div className="task-board">
      <h2>Task Board</h2>
      <div className="task-list">
        {sorted.map((task) => (
          <div key={task.id} className="task-card">
            <h3>{task.title}</h3>
            <span className="assignee">{task.assignee}</span>
            <span className="priority">{task.priority}</span>
            <span className="due-date">{task.dueDate}</span>
          </div>
        ))}
      </div>
    </div>
  );
};
