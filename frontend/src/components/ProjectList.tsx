import React from 'react';
import _ from 'lodash';
import { DateDisplay } from '@nexustrack/shared-ui';

export interface Project {
  id: number;
  name: string;
  description: string;
  status: string;
  createdAt: string;
}

interface ProjectListProps {
  projects: Project[];
  searchTerm?: string;
}

export const ProjectList: React.FC<ProjectListProps> = ({ projects, searchTerm }) => {
  const filtered = searchTerm
    ? projects.filter((p) => _.includes(p.name.toLowerCase(), searchTerm.toLowerCase()))
    : projects;

  if (filtered.length === 0) {
    return <div className="empty-state">No projects found</div>;
  }

  const grouped = _.groupBy(filtered, 'status');

  return (
    <div className="project-list">
      {Object.entries(grouped).map(([status, statusProjects]) => (
        <section key={status} className="project-group">
          <h2>{status}</h2>
          <ul>
            {statusProjects.map((project) => (
              <li key={project.id} className="project-item">
                <h3>{project.name}</h3>
                <p>{project.description}</p>
                <DateDisplay date={project.createdAt} relative />
              </li>
            ))}
          </ul>
        </section>
      ))}
    </div>
  );
};
