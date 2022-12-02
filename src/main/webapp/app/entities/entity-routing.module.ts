import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'announcement',
        data: { pageTitle: 'educationAppFinalApp.announcement.home.title' },
        loadChildren: () => import('./announcement/announcement.module').then(m => m.AnnouncementModule),
      },
      {
        path: 'target',
        data: { pageTitle: 'educationAppFinalApp.target.home.title' },
        loadChildren: () => import('./target/target.module').then(m => m.TargetModule),
      },
      {
        path: 'task',
        data: { pageTitle: 'educationAppFinalApp.task.home.title' },
        loadChildren: () => import('./task/task.module').then(m => m.TaskModule),
      },
      {
        path: 'participiant',
        data: { pageTitle: 'educationAppFinalApp.participiant.home.title' },
        loadChildren: () => import('./participiant/participiant.module').then(m => m.ParticipiantModule),
      },
      {
        path: 'period',
        data: { pageTitle: 'educationAppFinalApp.period.home.title' },
        loadChildren: () => import('./period/period.module').then(m => m.PeriodModule),
      },
      {
        path: 'course',
        data: { pageTitle: 'educationAppFinalApp.course.home.title' },
        loadChildren: () => import('./course/course.module').then(m => m.CourseModule),
      },
      {
        path: 'groups',
        data: { pageTitle: 'educationAppFinalApp.groups.home.title' },
        loadChildren: () => import('./groups/groups.module').then(m => m.GroupsModule),
      },
      {
        path: 'application',
        data: { pageTitle: 'educationAppFinalApp.application.home.title' },
        loadChildren: () => import('./application/application.module').then(m => m.ApplicationModule),
      },
      {
        path: 'attachment',
        data: { pageTitle: 'educationAppFinalApp.attachment.home.title' },
        loadChildren: () => import('./attachment/attachment.module').then(m => m.AttachmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
