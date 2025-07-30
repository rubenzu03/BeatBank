
import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Song List title', () => {
  render(<App />);
  const titleElement = screen.getByText(/Song List/i);
  expect(titleElement).toBeInTheDocument();
});
