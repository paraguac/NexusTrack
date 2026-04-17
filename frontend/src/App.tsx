import React from 'react';
import { ProjectList } from './components/ProjectList';

const App: React.FC = () => {
  return (
    <div className="app">
      <header>
        <h1>NexusTrack</h1>
        <p>Project Management Platform</p>
      </header>
      <main>
        <ProjectList projects={[]} />
      </main>
    </div>
  );
};

export default App;
