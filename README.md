# 💰 PennyWise

> A production-grade personal finance platform built with Spring Boot, PostgreSQL, Redis, Next.js, and Machine Learning.

> 🚧 **Status:** Active Development

---

## Overview

PennyWise is a full-stack personal finance application that helps users understand and manage their spending by automatically importing bank statements, categorising transactions, tracking budgets, and providing intelligent financial insights.

Unlike a simple expense tracker, PennyWise is being designed as a scalable, production-ready backend that demonstrates modern software engineering practices including security, caching, asynchronous processing, containerisation, and machine learning integration.

This project is being built as a portfolio piece with an emphasis on clean architecture, maintainability, and real-world engineering practices.

---

## Planned Features

### Authentication
- JWT Authentication
- Refresh Token Rotation
- Spring Security
- BCrypt Password Hashing
- Role-Based Access Control

### Transaction Management
- Import password-protected bank statements (PDF & Excel)
- Automatic transaction extraction
- CRUD operations
- Search & filtering
- Pagination
- Monthly summaries

### Smart Categorisation
- AI-powered transaction categorisation
- User-defined category aliases
- Category confidence scoring

### Budget Management
- Monthly budgets
- Spending progress
- Budget alerts
- Category-wise analytics

### Analytics Dashboard
- Income vs Expense
- Monthly trends
- Spending heatmaps
- Cash flow analysis

### Machine Learning (Planned)
- Expense prediction
- Spending anomaly detection
- Intelligent budgeting recommendations

---

## Tech Stack

### Backend

- Java 25
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Redis
- Flyway
- Maven
- JWT Authentication

### Frontend

- Next.js
- React
- Tailwind CSS
- Shadcn UI
- Recharts

### Machine Learning

- Python
- PyTorch
- scikit-learn
- FastAPI

### DevOps

- Docker
- Docker Compose
- GitHub Actions

---

## Project Structure

```
pennywise/
│
├── backend/
├── frontend/
├── ml-service/
├── docker/
├── docs/
├── bruno/
└── README.md
```

---

## Current Progress

### Completed

- Project setup
- Spring Boot configuration
- PostgreSQL integration
- Flyway migrations
- JWT Authentication
- User Registration
- Login
- Refresh Token Rotation
- Spring Security configuration

### In Progress

- Global Exception Handling
- Standardised API Responses

### Next Milestone

- Transaction Module
- Bank Statement Import
- Dashboard APIs

---

## Long-Term Goals

- Import bank statements from multiple banks
- AI-powered expense categorisation
- Spending predictions
- Budget recommendations
- Investment portfolio tracking
- Financial health scoring

---

## Why PennyWise?

This project focuses on demonstrating:

- Clean Architecture
- Secure Authentication
- REST API Design
- Database Design
- Caching with Redis
- Background Processing
- Machine Learning Integration
- Docker-based Deployment
- CI/CD Pipelines

Rather than being just another CRUD application, PennyWise aims to simulate the architecture and engineering practices used in production fintech applications.

---

## Development Status

This repository is under active development.

Features, APIs, and documentation will continue to evolve as new modules are implemented.

```

## Planned Documentation

- API Documentation
- Architecture Diagrams
- Database Schema
- Deployment Guide
- Contribution Guide

---
