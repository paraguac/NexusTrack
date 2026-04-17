import React from 'react';
import { render, screen } from '@testing-library/react';
import { ProjectList, Project } from '../components/ProjectList';

const mockProjects: Project[] = [
  { id: 1, name: 'Alpha', description: 'First project', status: 'ACTIVE', createdAt: '2024-01-15' },
  { id: 2, name: 'Beta', description: 'Second project', status: 'ACTIVE', createdAt: '2024-02-20' },
  { id: 3, name: 'Gamma', description: 'Third project', status: 'ARCHIVED', createdAt: '2024-03-10' },
];

describe('ProjectList', () => {
  test('renders all projects', () => {
    render(<ProjectList projects={mockProjects} />);
    expect(screen.getByText('Alpha')).toBeInTheDocument();
    expect(screen.getByText('Beta')).toBeInTheDocument();
    expect(screen.getByText('Gamma')).toBeInTheDocument();
  });

  test('shows empty state when no projects', () => {
    render(<ProjectList projects={[]} />);
    expect(screen.getByText('No projects found')).toBeInTheDocument();
  });

  test('filters by search term', () => {
    render(<ProjectList projects={mockProjects} searchTerm="alpha" />);
    expect(screen.getByText('Alpha')).toBeInTheDocument();
    expect(screen.queryByText('Beta')).not.toBeInTheDocument();
    expect(screen.queryByText('Gamma')).not.toBeInTheDocument();
  });

  test('groups projects by status', () => {
    render(<ProjectList projects={mockProjects} />);
    expect(screen.getByText('ACTIVE')).toBeInTheDocument();
    expect(screen.getByText('ARCHIVED')).toBeInTheDocument();
  });
});
